import React, {Component} from "react";
import {Tabs} from 'antd';
import IssueNew from "./article/IssueNew";
import Contribute from "./article/Contribute";

const {TabPane} = Tabs;

/**
 * @author Yuicon
 */

class New extends Component {

    render() {

        return (
            <div>
                <Tabs defaultActiveKey="1">
                    <TabPane tab="富文本投稿" key="1">
                        <Contribute/>
                    </TabPane>
                    <TabPane tab="issue 投稿" key="2">
                        <IssueNew/>
                    </TabPane>
                </Tabs>
            </div>
        );
    }
}

export default New;