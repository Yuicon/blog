/**
 * @author Yuicon
 */
import React, {Component} from "react";
import {Modal, Input, message} from "antd";
import {userApi} from "../api/userApi";

class Login extends Component {

    constructor() {
        super();
        this.state = {
            email: null,
            password: null
        };
    }

    handleEmail = (e) => {
        this.setState({email: e.target.value})
    };

    handlePassword = (e) => {
        this.setState({password: e.target.value})
    };

    handleOk = async () => {
        const body = await userApi.login(this.state.email, this.state.password);
        if (body.success) {
            this.props.handleOk(body.data);
        } else {
            message.error(body.message);
        }
    };

    render() {

        return (
            <Modal title="用户登录"
                   maskClosable={false}
                   visible={this.props.visible}
                   onOk={this.handleOk}
                   onCancel={this.props.handleCancel}>
                <Input addonBefore="邮箱" type="email" onChange={this.handleEmail} />
                <Input.Password addonBefore="密码" onChange={this.handlePassword}/>
            </Modal>
        );
    }
}

export default Login;