/**
 * @author Yuicon
 */
import React, {Component} from "react";
import Login from "./Login";
import { Button } from 'antd';

class UserInfo extends Component {

    constructor() {
        super();
        this.state = {
            username: localStorage.getItem("username"),
            loginVisible: false
        };
    }

    handleOnLoginOk = (userData) => {
        localStorage.setItem("accessToken", userData.accessToken);
        localStorage.setItem("username", userData.username);
        this.setState({loginVisible: false, username: userData.username});
    };

    handleOnLoginCancel = () => {
        this.setState({loginVisible: false});
    };

    login = () => {
        this.setState({loginVisible: true});
    };

    render() {
        return (
            <div className="username">
                <Login visible={this.state.loginVisible} handleOk={this.handleOnLoginOk} handleCancel={this.handleOnLoginCancel}/>
                <h3>{this.state.username ? this.state.username : <Button onClick={this.login}>登录</Button>}</h3>
            </div>
        );
    }
}

export default UserInfo;
