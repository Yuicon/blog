import React from 'react';
import Editor from "./Editor";
import {Button, Card, Comment, message, Modal, Tooltip} from "antd";
import {commentApi} from "../../api/articleApi";
import BraftEditor from "braft-editor";
import moment from "moment";

export default class CommentBase extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            content: '',
            comments: [],
            visible: false,
            quoteId: 0
        };
    }

    componentDidMount() {
        this.getComments();
    }

    getComments = async (e, current = 1, size = 100) => {
        const body = await commentApi.findAll(this.props.articleId, current, size);
        if (!body.success) {
            message.error(body.message);
            return;
        }
        const comments = body.data.records;
        const floors = {};
        comments.filter(comment => comment.quoteId === 0).forEach(comment => {
            floors[comment.id] = [comment];
        });
        comments.filter(comment => comment.quoteId !== 0).reverse().forEach(comment => {
            floors[comment.id] = floors[comment.quoteId].concat();
            floors[comment.id].push(comment);
        });
        const commentList = [];
        comments.forEach(comment => {
            commentList.push(floors[comment.id]);
        });
        this.setState({comments: commentList});
    };

    sentComment = async () => {
        if (this.state.content.length < 140) {
            message.warn("字数不足!");
            return;
        }

        if (this.state.content.length > 5400) {
            message.warn("字数过多!");
            return;
        }
        const body = await commentApi.save({
            content: this.state.content,
            articleId: this.props.articleId,
            quoteId: this.state.quoteId,
            state: 10
        });
        if (body.success && body.data) {
            this.setState({content: ''});
            message.success("发送成功!");
            this.getComments();
        } else {
            message.error(body.message);
        }
        this.setState({visible: false});
    };

    replyHandle = (comment) => {
        this.setState({quoteId: comment.id, visible: true});
    };

    render() {

        return (
            <div>
                <Card bordered={false}
                      extra={<Button type="primary" onClick={this.sentComment} htmlType="button">发送评论</Button>}>
                    <div style={{
                        backgroundColor: "#e2e2e261",
                        height: "200px"
                    }}>
                        <Editor onChange={html => this.setState({content: html})}
                                controls={['bold', 'italic', 'underline', 'strike-through', 'emoji', 'text-color']}
                        />
                    </div>
                </Card>
                <Card bordered={false}
                      extra={<Button type="primary" onClick={this.getComments} htmlType="button">刷新评论</Button>}>
                    {
                        this.state.comments.length > 0 && <div>
                            {
                                this.state.comments.map((commentList, i) => {
                                    return <div key={i} style={{padding: "30px"}}>
                                        {
                                            commentList.map((comment, index) => {
                                                const editorState = BraftEditor.createEditorState(comment.content);
                                                const content = editorState.toHTML();
                                                const style = {border: '1px solid #fff4f4b5', textAlign: "left"};
                                                if (index !== (commentList.length - 1)) {
                                                    style.backgroundColor = "#fff8dc82";
                                                }
                                                return <Comment
                                                    style={style}
                                                    actions={[<span key="comment-nested-reply-to"
                                                                    onClick={this.replyHandle.bind(this, comment)}>回复</span>]}
                                                    key={comment.id}
                                                    author={<a>{`${comment.floor}L - ${comment.accountName}`}</a>}
                                                    content={<article dangerouslySetInnerHTML={{__html: content}}
                                                                      style={{textAlign: 'left'}}/>}
                                                    datetime={
                                                        <Tooltip title={moment().format(comment.createTime)}>
                                                            <span>{moment(comment.createTime).fromNow()}</span>
                                                        </Tooltip>
                                                    }
                                                />;
                                            })
                                        }
                                    </div>;
                                })
                            }
                        </div>
                    }
                </Card>
                <Modal
                    title="回复"
                    visible={this.state.visible}
                    onOk={this.sentComment}
                    onCancel={() => this.setState({visible: false, quoteId: 0})}
                    afterClose={() => this.setState({visible: false, quoteId: 0})}
                >
                    <Editor onChange={html => this.setState({content: html})}
                            controls={['bold', 'italic', 'underline', 'strike-through', 'emoji', 'text-color']}/>
                </Modal>
            </div>
        )

    }

}