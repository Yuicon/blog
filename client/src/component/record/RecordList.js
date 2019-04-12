/**
 * @author Yuicon
 */
import React, {Component} from "react";
import {Button, message, PageHeader, Tag, Menu} from "antd";
import {TOKEN_KEY} from "../../constant";
import {recordApi} from "../../api/recordApi";
import RecordForm from "./RecordForm";
import "./Record.css";

const SubMenu = Menu.SubMenu;

class RecordList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            records: [],
            formVisible: false
        };
    }

    componentDidMount() {
        if (!localStorage.getItem(TOKEN_KEY)) {
            this.props.history.push("/");
        }
        this.list();
    }

    list = async () => {
        this.setState({spinning: true});
        const body = await recordApi.list();
        if (body.success) {
            this.setState({records: body.data}, () => console.log(this.state));
        } else {
            message.error(body.message);
        }
        this.setState({
            spinning: false
        });
    };

    handleOnInsertOk = () => {
        this.setState({formVisible: false}, this.list);
    };

    handleOnInsertCancel = () => {
        this.setState({formVisible: false});
    };

    onClick = () => {
        this.setState({formVisible: true});
    };

    handleClick = (e) => {
        console.log('click', e);
    };

    render() {

        const groups = {};

        this.state.records.forEach(record => {
            if (!groups[record.group]) {
                groups[record.group] = [];
            }
            groups[record.group].push(record);
        });

        const menus = Object.keys(groups).map(group => {
            return <SubMenu key={group} title={<span>{group}</span>}>
                {groups[group].map(record => <Menu.Item key={record.id}>{record.source}</Menu.Item>)}
            </SubMenu>;
        });

        return (
            <div>
                <RecordForm visible={this.state.formVisible} handleOk={this.handleOnInsertOk}
                            handleCancel={this.handleOnInsertCancel}/>
                <PageHeader
                    onBack={() => window.history.back()}
                    title="记录"
                    subTitle="在这里,看到真实有趣的世界,找到自己感兴趣的人,也可以让世界发现真实有趣的自己。记录世界记录你。"
                    tags={<Tag color="blue">保密</Tag>}
                    extra={[
                        <Button key={1} onClick={this.onClick} type="primary">
                            创建
                        </Button>,
                    ]}
                >
                </PageHeader>
                <div className="record-list">
                    <Menu
                        onClick={this.handleClick}
                        mode="inline"
                        style={{ width: 256 }}
                    >
                        {menus}
                    </Menu>
                </div>
            </div>
        );
    }
}

export default RecordList;