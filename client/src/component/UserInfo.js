/**
 * @author Yuicon
 */
import React, {Component} from "react";
import Login from "./Login";
import {Button, Tabs} from 'antd';
import Register from "./Register";
import {withRouter} from "react-router-dom";
import {TOKEN_KEY} from "../constant";

const {TabPane} = Tabs;

class UserInfo extends Component {

    handleOnLoginOk = (userData) => {
        localStorage.setItem("accessToken", userData.accessToken);
        localStorage.setItem("username", userData.username);
        this.setState({loginVisible: false, username: userData.username});
    };

    handleOnLoginCancel = () => {
        this.setState({loginVisible: false});
    };

    handleOnRegisterOk = () => {
        this.setState({registerVisible: false, loginVisible: true});
    };

    handleOnRegisterCancel = () => {
        this.setState({registerVisible: false});
    };

    login = () => {
        this.setState({loginVisible: true});
    };

    register = () => {
        this.setState({registerVisible: true});
    };
    callback = (key) => {
        const func = this.menuMap[key];
        func();
        console.log('click', key);
    };

    constructor(props) {
        super(props);
        let defaultActiveKey;
        switch (window.location.pathname) {
            case "/record":
                defaultActiveKey = "record";
                break;
            case "/article/new":
                defaultActiveKey = "upload";
                break;
            default:
                defaultActiveKey = 'index';
        }
        this.state = {
            username: localStorage.getItem("username"),
            loginVisible: false,
            registerVisible: false,
            defaultActiveKey: defaultActiveKey
        };
        this.menuMap = {
            "upload": () => this.props.history.push("/article/new"),
            "index": () => this.props.history.push("/"),
            "logout": () => {
                localStorage.removeItem(TOKEN_KEY);
                localStorage.removeItem("username");
                this.setState({username: localStorage.getItem("username")});
            },
            "record": () => this.props.history.push("/record"),
        }
    }

    render() {
        return (
            <div>
                <Login visible={this.state.loginVisible} handleOk={this.handleOnLoginOk}
                       handleCancel={this.handleOnLoginCancel}/>
                <Register visible={this.state.registerVisible} handleOk={this.handleOnRegisterOk}
                          handleCancel={this.handleOnRegisterCancel}/>
                <h3>{this.state.username ?
                    <div>
                        <div>
                            {this.state.username}
                        </div>
                        <div className="username">
                            <Tabs onChange={this.callback} size="small" defaultActiveKey={this.state.defaultActiveKey}>
                                <TabPane tab="首页" key="index"/>
                                <TabPane tab="投稿" key="upload"/>
                                <TabPane tab="记录" key="record"/>
                                <TabPane tab="退出" key="logout"/>
                            </Tabs>
                        </div>
                    </div>
                    :
                    <div><Button onClick={this.login} type="primary" htmlType="button">登录</Button>&nbsp;<Button
                        onClick={this.register} htmlType="button">注册</Button>
                    </div>}</h3>
            </div>
        );
    }
}

export default withRouter(UserInfo);
