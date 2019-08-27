import React, {Component} from "react";
import {Button, Input} from 'antd';
import Editor from "./Editor";

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
            articleType: 1
        };
    }


    async componentDidMount() {

    }

    save = () => {

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
                <div style={{backgroundColor: "#e2e2e261"}}><Editor onChange={html => this.setState({content: html})}/>
                </div>
                <div style={{padding: "20px"}}>
                    <Button onClick={this.save} htmlType="button" type="primary">保存</Button>
                </div>
            </div>
        );
    }
}

export default Contribute;