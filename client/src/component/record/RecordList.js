/**
 * @author Yuicon
 */
import React, {Component} from "react";
import {Button, message, PageHeader, Tag, Menu, Table} from "antd";
import {TOKEN_KEY} from "../../constant";
import {recordApi} from "../../api/recordApi";
import RecordForm from "./RecordForm";
import "./Record.css";
import ItemForm from "./ItemForm";

const SubMenu = Menu.SubMenu;

class RecordList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            records: [],
            items: [],
            rid: null,
            formVisible: false,
            itemFormVisible: false,
            record: {},
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

    itemList = async () => {
        this.setState({spinning: true});
        const body = await recordApi.items(this.state.rid);
        body.success ? this.setState({items: body.data}) : message.error(body.message);
        this.setState({
            spinning: false
        });
    };

    handleOnInsertOk = () => {
        this.setState({formVisible: false}, this.list);
    };

    handleOnItemInsertOk = () => {
        this.setState({itemFormVisible: false}, this.itemList);
    };

    handleOnInsertCancel = () => {
        this.setState({formVisible: false, itemFormVisible: false, record: {}});
    };

    onClick = () => {
        this.setState({formVisible: true});
    };

    handleClick = async (e) => {
        const rid = e.key;
        this.setState({rid: rid}, this.itemList);
    };

    handleItemClick = () => {
        this.setState({itemFormVisible: true});
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

        const dataSource = this.state.items;

        const columns = [{
            title: '说明',
            dataIndex: 'label',
            key: 'label',
        }, {
            title: '数据',
            dataIndex: 'value',
            key: 'value',
            render: (text, record, index) => {
                if (record.kind === 1) {
                    return "******"
                }
                return text;
            }
        }];

        return (
            <div>
                <RecordForm visible={this.state.formVisible} handleOk={this.handleOnInsertOk}
                            handleCancel={this.handleOnInsertCancel}/>
                <ItemForm visible={this.state.itemFormVisible} handleOk={this.handleOnItemInsertOk}
                          handleCancel={this.handleOnInsertCancel} recordId={this.state.rid}
                          record={this.state.record}
                />
                <PageHeader
                    onBack={() => window.history.back()}
                    title="记录"
                    subTitle="在这里,看到真实有趣的世界,找到自己感兴趣的人,也可以让世界发现真实有趣的自己。记录世界记录你。"
                    tags={<Tag color="blue">保密</Tag>}
                    extra={[
                        <Button key={1} onClick={this.onClick} type="primary">
                            创建记录
                        </Button>,
                    ]}
                >
                </PageHeader>
                <div className="record-list">
                    <Menu
                        onClick={this.handleClick}
                        mode="inline"
                        style={{width: 256}}
                    >
                        {menus}
                    </Menu>
                    <div className="item">
                        <Table dataSource={dataSource} columns={columns} pagination={{showSizeChanger: true, showTotal: total => `Total ${total}`}}
                               rowKey={record => record.id}
                               title={() => this.state.rid && <Button onClick={this.handleItemClick} type="primary">创建条目</Button>}
                               onRow={(record) => {
                                   return {
                                       onClick: (event) => {
                                           this.setState({record: record}, this.handleItemClick);
                                       },       // 点击行
                                   };
                               }}
                        />
                    </div>
                </div>
            </div>
        );
    }
}

export default RecordList;