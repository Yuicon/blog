import React from 'react';
import Editor from "./Editor";
import {Button, Collapse, Comment, message, Tooltip} from "antd";
import {commentApi} from "../../api/articleApi";
import BraftEditor from 'braft-editor'
import moment from "moment";

const {Panel} = Collapse;

export default class CommentBase extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            content: '',
            comments: [],
        };
    }

    componentDidMount() {
        this.getComments();
    }

    getComments = async (current = 1, size = 100) => {
        const body = await commentApi.findAll(this.props.articleId, current, size);
        if (!body.success) {
            message.error(body.message);
            return;
        }
        this.setState({comments: body.data.records});
    };

    sentComment = async (e, quoteId) => {
        if (this.state.content.length < 10) {
            message.warn("字数不足!");
            return;
        }
        const body = await commentApi.save({
            content: this.state.content,
            articleId: this.props.articleId,
            quoteId: quoteId,
            state: 10
        });
        if (body.success && body.data) {
            this.setState({content: ''});
            message.success("发送成功!");
            this.getComments();
        } else {
            message.error(body.message);
        }
    };

    render() {

        return (
            <div>
                <Collapse defaultActiveKey={['1']}>
                    <Panel header="评论" key="1">
                        {
                            this.state.comments.length > 0 && <div style={{border: '1px solid gainsboro'}}>
                                {
                                    this.state.comments.map(comment => {
                                        const editorState = BraftEditor.createEditorState(comment.content);
                                        const content = editorState.toHTML();
                                        return <Comment
                                            author={<a>{comment.accountName}</a>}
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
                            </div>
                        }
                    </Panel>
                    <Panel header="发表评论" key="2">
                        <div style={{
                            width: '900px',
                            backgroundColor: "#e2e2e261"
                        }}>
                            <Editor onChange={html => this.setState({content: html})}/>
                        </div>
                        <div>
                            <Button type="primary" onClick={this.sentComment} htmlType="button">发送评论</Button>
                        </div>
                    </Panel>
                </Collapse>
            </div>
        )

    }

}