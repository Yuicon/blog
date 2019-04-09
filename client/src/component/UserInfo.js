/**
 * @author Yuicon
 */
import React, {Component} from "react";
import Login from "./Login";
import {Button} from 'antd';
import Register from "./Register";
import {withRouter} from "react-router-dom";

class UserInfo extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: localStorage.getItem("username"),
            loginVisible: false,
            registerVisible: false,
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

    handleOnRegisterOk = () => {
        this.setState({registerVisible: false, loginVisible: true});
    };

    handleOnRegisterCancel = () => {
        this.setState({registerVisible: false});
    };

    login = () => {
        this.setState({loginVisible: true});
    };

    logout = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("username");
        this.setState({username: localStorage.getItem("username")});
    };

    upload = () => {
        this.props.history.push("/article/new");
    };

    register = () => {
        this.setState({registerVisible: true});
    };

    render() {
        return (
            <div className="username">
                <Login visible={this.state.loginVisible} handleOk={this.handleOnLoginOk}
                       handleCancel={this.handleOnLoginCancel}/>
                <Register visible={this.state.registerVisible} handleOk={this.handleOnRegisterOk}
                       handleCancel={this.handleOnRegisterCancel}/>
                <h3>{this.state.username ? <div>{this.state.username}&nbsp;<Button onClick={this.upload} type="primary">投稿</Button>&nbsp;<Button onClick={this.logout} type="danger">退出</Button></div> :
                    <div><Button onClick={this.login} type="primary">登录</Button>&nbsp;<Button onClick={this.register}>注册</Button>
                    </div>}</h3>
            </div>
        );
    }
}

export default withRouter(UserInfo);
