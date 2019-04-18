/**
 * @author Yuicon
 */
import React, {Component} from "react";
import {Modal, Input, message} from "antd";
import {recordApi} from "../../api/recordApi";

class RecordForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            source: null,
            group: null,
        };
    }

    handleChange = (e) => {
        this.setState({source: e.target.value})
    };

    handleGroupChange = (e) => {
        this.setState({group: e.target.value})
    };

    handleOk = async () => {
        const body = await recordApi.insert(this.state.source, this.state.group);
        if (body.success) {
            this.props.handleOk(body.data);
        } else {
            message.error(body.message);
        }
    };

    render() {

        return (
            <Modal title="记录世界"
                   maskClosable={false}
                   visible={this.props.visible}
                   onOk={this.handleOk}
                   onCancel={this.props.handleCancel}>
                <Input addonBefore="来源" onChange={this.handleChange} />
                <Input addonBefore="分组" onChange={this.handleGroupChange} />
            </Modal>
        );
    }
}

export default RecordForm;