import React, {Component} from "react";
import {blogApi} from "../api/blogApi";
import Entry from "./Entry";
import { Spin } from 'antd';

/**
 * @author Yuicon
 */

class Home extends Component {

    constructor() {
        super();
        this.state = {
            articles: [],
            spinning: false
        }
    }

    async componentDidMount() {
        this.setState({spinning: true});
        const body = await blogApi.getArticles();
        this.setState({
            articles: body.content,
            spinning: false
        });
    }

    render() {

        const entryList = this.state.articles.map(article => <Entry history={this.props.history} article={article} key={article.id}/>);

        return (
            <div className="entry-list">
                <Spin spinning={this.state.spinning} size="large"/>
                {entryList}
            </div>
        );
    }
}

export default Home;