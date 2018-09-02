import React, {Component} from "react";
import {api} from "./api";

/**
 * @author Yuicon
 */

class New extends Component {

    constructor() {
        super();
        this.state = {
            gitUserName: "",
            repositoryName: "",
            issueId: 0
        };
        this.handle = {
            "gitUserName": value => this.setState({gitUserName: value}),
            "repositoryName": value => this.setState({repositoryName: value}),
            "issueId": value => this.setState({issueId: value}),
        };
    }

    handleOnChange = (event) => {
        this.handle[event.target.id](event.target.value);
    };

    handleOnClick = async () => {
        console.log(this.state);
        if (this.state.gitUserName !== "" && this.state.repositoryName !== "") {
            const gitIssue = await api.getArticle(this.state.gitUserName, this.state.repositoryName, this.state.issueId);
            if (gitIssue.message !== undefined) {
                alert(gitIssue.message);
            } else {
                const newArticle = await api.addArticle(this.state.gitUserName, this.state.repositoryName, this.state.issueId);
                await api.putArticle(newArticle.id, gitIssue.title, gitIssue.body,
                    gitIssue.created_at, gitIssue.updated_at, gitIssue.closed_at);
                alert("新建成功");
            }
        } else {
            alert("请输入");
        }
    };

    render() {

        return (
            <div className="new">
                <label htmlFor="gitUserName">GitHub用户名</label>
                <input type="text" onChange={this.handleOnChange} id="gitUserName" placeholder="请输入你的GitHub用户名"/>
                <label htmlFor="repositoryName">仓库名</label>
                <input type="text" onChange={this.handleOnChange} id="repositoryName" placeholder="请输入你的仓库名"/>
                <label htmlFor="issueId">Issue Id</label>
                <input type="text" onChange={this.handleOnChange} id="issueId" placeholder="请输入你的Issue Id"/>
                <button onClick={this.handleOnClick}>提交</button>
            </div>
        );
    }
}

export default New;