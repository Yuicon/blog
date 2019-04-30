/**
 * @author Yuicon
 */
import React, {Component} from "react";
import {Modal, Input, message} from "antd";
import {userApi} from "../api/userApi";

class Register extends Component {

    constructor(props) {
        super(props);
        this.state = {
            email: null,
            password: null,
            username: null,
            phone: null
        };
    }

    handleEmail = (e) => {
        this.setState({email: e.target.value})
    };

    handleUsername = (e) => {
        this.setState({username: e.target.value})
    };

    handlePhone = (e) => {
        this.setState({phone: e.target.value})
    };

    handlePassword = (e) => {
        this.setState({password: e.target.value})
    };

    handleOk = async () => {
        const body = await userApi.register(this.state.email, this.state.username, this.state.password, this.state.phone);
        if (body.success) {
            message.success(body.message);
            this.props.handleOk(body.data);
        } else {
            message.error(body.message);
        }
    };

    render() {

        return (
            <Modal title="用户注册"
                   maskClosable={false}
                   visible={this.props.visible}
                   onOk={this.handleOk}
                   onCancel={this.props.handleCancel}>
                <Input addonBefore="邮箱" type="email" onChange={this.handleEmail} />
                <Input addonBefore="昵称" onChange={this.handleUsername} />
                <Input addonBefore="手机" onChange={this.handlePhone} />
                <Input.Password addonBefore="密码" onChange={this.handlePassword}/>
            </Modal>
        );
    }
}

export default Register;