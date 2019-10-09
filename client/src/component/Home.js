import React, {Component} from "react";
import {commonArticleApi} from "../api/blogApi";
import Entry from "./Entry";
import {Pagination, Spin} from 'antd';

/**
 * @author Yuicon
 */

class Home extends Component {

    constructor(props) {
        super(props);
        this.state = {
            articles: [],
            spinning: false,
            total: 0,
            current: 1
        }
    }

    componentDidMount() {
        this.getArticle(1, 10);
    }

    getArticle = async (page, pageSize) => {
        this.setState({spinning: true});
        const body = await commonArticleApi.findAll(page, pageSize);
        this.setState({
            articles: body.data.records || [],
            spinning: false,
            total: body.data.total,
            current: body.data.current,
        });
    };

    render() {

        const entryList = this.state.articles.map(article => <Entry history={this.props.history} article={article} key={article.id}/>);

        return (
            <div className="entry-list">
                <Spin spinning={this.state.spinning} size="large"/>
                {entryList}
                <Pagination defaultCurrent={1} total={this.state.total} current={this.state.current}
                            showQuickJumper={true} showSizeChanger={true} onChange={this.getArticle}
                            onShowSizeChange={this.getArticle}/>
            </div>
        );
    }
}

export default Home;