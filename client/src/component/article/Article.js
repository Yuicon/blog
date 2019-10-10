import React, {Component} from "react";
import {message, Spin} from 'antd';
import {articleApi} from "../../api/articleApi";
import BraftArticle from "./BraftArticle";
import V2exArticle from "./V2exArticle";
import MarkDownArticle from "./MarkDownArticle";
import CommentBase from "./CommentBase";

/**
 * @author Yuicon
 */

export default class Article extends Component {

    constructor(props) {
        super(props);
        this.state = {
            article: {},
            error: "",
            spinning: false,
            loadingComment: false
        };
    }

    backTop = () => {
        window.scrollTo(0,0);
    };

    async componentDidMount() {
        this.setState({spinning: true});
        const body = await articleApi.getArticleById(this.props.match.params.id);
        if (!body.success) {
            message.error(body.message);
            return;
        }
        this.setState({article: body.data, spinning: false, loadingComment: true});
        window._hmt.push(['_trackPageview', `/articles/${body.data.id}`]);
    }

    renderArticle = (type) => {
        if (type === undefined) {
            return <div/>;
        }
        const map = {
            1: <BraftArticle article={this.state.article}/>,
            2: <V2exArticle article={this.state.article}/>,
            3: <MarkDownArticle article={this.state.article}/>,
        };
        return map[type];
    };

    render() {

        return (
            <div className="article-content">
                <Spin spinning={this.state.spinning} size="large"/>
                <h1>{this.state.article.title}</h1>
                <h3>{this.state.article.introduction}</h3>
                <div style={{width: '60%'}}>
                    {this.renderArticle(this.state.article.articleType)}
                </div>
                <div className="suspension-panel">
                    <button title="回到顶部" className="btn to-top-btn" onClick={this.backTop}>
                        <i className="ion-android-arrow-dropup"/>
                    </button>
                </div>
                <div style={{width: "60%"}}>
                    {this.state.loadingComment && <CommentBase articleId={this.state.article.id}/>}
                </div>
            </div>
        );
    }
}