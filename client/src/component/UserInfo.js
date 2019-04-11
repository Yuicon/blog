/**
 * @author Yuicon
 */
import React, {Component} from "react";
import Login from "./Login";
import {Button} from 'antd';
import Register from "./Register";
import {withRouter} from "react-router-dom";
import {Menu} from 'antd';

const SubMenu = Menu.SubMenu;

class UserInfo extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: localStorage.getItem("username"),
            loginVisible: false,
            registerVisible: false,
        };
        this.menuMap = {
            "upload": this.upload,
            "logout": this.logout,
        }
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

    handleClick = (e) => {
        const func = this.menuMap[e.key];
        func();
        console.log('click', e);
    };

    render() {
        return (
            <div>
                <Login visible={this.state.loginVisible} handleOk={this.handleOnLoginOk}
                       handleCancel={this.handleOnLoginCancel}/>
                <Register visible={this.state.registerVisible} handleOk={this.handleOnRegisterOk}
                          handleCancel={this.handleOnRegisterCancel}/>
                <h3>{this.state.username ?
                    <div className="username">{this.state.username}&nbsp;
                        <Menu onClick={this.handleClick} style={{width: 100}} mode="vertical">
                            <SubMenu key="other" title={<span><span>其他</span></span>}>
                                <Menu.Item key="upload">投稿</Menu.Item>
                                <Menu.Item key="logout">退出</Menu.Item>
                            </SubMenu>
                        </Menu>
                    </div>
                    :
                    <div><Button onClick={this.login} type="primary">登录</Button>&nbsp;<Button
                        onClick={this.register}>注册</Button>
                    </div>}</h3>
            </div>
        );
    }
}

export default withRouter(UserInfo);
