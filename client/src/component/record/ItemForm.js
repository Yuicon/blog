/**
 * @author Yuicon
 */
import React, {Component} from "react";
import {Modal, Input, message, InputNumber, Switch} from "antd";
import {recordApi} from "../../api/recordApi";

class ItemForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            label: null,
            value: null,
            sequence: 0,
            kind: 0,
            state: 0,
            record: {}
        };
    }

    static getDerivedStateFromProps(props, state) {
        if (props.record !== state.record) {
            return {
                label: props.record.label,
                value: props.record.value,
                sequence: props.record.sequence,
                kind: props.record.kind,
                state: props.record.state,
                record: props.record,
            }
        }
        return null;
    }

    handleLabelChange = (e) => {
        this.setState({label: e.target.value})
    };

    handleValueChange = (e) => {
        this.setState({value: e.target.value})
    };

    handleKindChange = (checked, event) => {
        this.setState({kind: checked ? 0 : 1});
    };

    handleStateChange = (checked, event) => {
        this.setState({state: checked ? 0 : 1});
    };

    handleSequenceChange = (value) => {
        this.setState({sequence: value});
    };

    handleOk = async () => {
        console.log(this.state);
        let body;
        if (this.props.record.id) {
            body = await recordApi.updateItem(this.props.record.id, this.state.label, this.state.value,
                this.state.sequence, this.state.kind, this.state.state)
        } else {
            body = await recordApi.insertItem(this.props.recordId, this.state.label, this.state.value,
                this.state.sequence, this.state.kind);
        }
        if (body.success) {
            message.success(body.message);
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
                <Input addonBefore="说明" value={this.state.label} onChange={this.handleLabelChange}/>
                <Input addonBefore="数据" value={this.state.value} onChange={this.handleValueChange}/>
                <span className="ant-input-group-wrapper">
                    <span className="ant-input-wrapper ant-input-group">
                        <span className="ant-input-group-addon">顺序</span>
                        <InputNumber defaultValue={0} value={this.state.sequence} onChange={this.handleSequenceChange}/>
                    </span>
                </span>
                <Switch checkedChildren="公开" unCheckedChildren="私密" checked={this.state.kind === 0} defaultChecked
                        onChange={this.handleKindChange}/>
                {
                    this.props.record.id &&
                    <Switch checkedChildren="正常" unCheckedChildren="删除" checked={this.state.state === 0} defaultChecked
                            onChange={this.handleStateChange}/>
                }
            </Modal>
        );
    }
}

export default ItemForm;