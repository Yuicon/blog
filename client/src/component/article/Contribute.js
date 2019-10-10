import React, {Component} from "react";
import {Button, Input, message} from 'antd';
import Editor from "./Editor";
import {articleApi} from "../../api/articleApi";

/**
 * @author Yuicon
 */

class Contribute extends Component {

    constructor(props) {
        super(props);
        this.state = {
            title: '',
            content: '',
            introduction: "",
            source: "self",
            articleType: 1,
            disabled: false
        };
    }

    save = async () => {
        if (this.state.title.length < 5 || this.state.title.length > 26) {
            message.warn("标题过短或过长!");
            return;
        }
        this.setState({disabled: true});

        const body = await articleApi.addArticle({
            title: this.state.title,
            body: this.state.content,
            introduction: this.state.introduction,
            source: this.state.source,
            articleType: this.state.articleType,
        });
        if (body.success) {
            message.success("投稿成功");
        } else {
            message.error(body.message);
        }
        this.setState({disabled: false});
        window.location.reload();
    };

    render() {

        return (
            <div className="contribute">
                <div style={{padding: "20px"}}>
                    <Input placeholder="请输入标题" addonBefore="标题"
                           onChange={event => this.setState({title: event.target.value})}/>
                </div>
                <div style={{padding: "20px"}}>
                    <Input placeholder="请输入简介" addonBefore="简介"
                           onChange={event => this.setState({introduction: event.target.value})}/>
                </div>
                <div style={{backgroundColor: "#e2e2e261"}}>
                    <Editor onChange={html => this.setState({content: html})}/>
                </div>
                <div style={{padding: "20px"}}>
                    <Button onClick={this.save} htmlType="button" type="primary"
                            disabled={this.state.disabled}>保存</Button>
                </div>
            </div>
        );
    }
}

export default Contribute;