import React, {Component} from "react";
import {blogApi} from "../../api/blogApi";
import 'highlight.js/styles/github-gist.css';
import hljs from 'highlight.js';
import 'gitalk/dist/gitalk.css'
import Gitalk from 'gitalk'
import { Spin } from 'antd';

/**
 * @author Yuicon
 */

class Article extends Component {

    constructor(props) {
        super(props);
        this.state = {
            article: {},
            error: "",
            spinning: false
        };
    }

    backTop = () => {
        window.scrollTo(0,0);
    };

    async componentDidMount() {
        this.setState({spinning: true});
        const article = await blogApi.getArticleById(this.props.match.params.id);
        this.setState({article: article.data, error: "<h1>没有文章哦！</h1>", spinning: false}, () => {
            const gitalk = new Gitalk({
                clientID: '1f4bdb550130e267946f',
                clientSecret: '1b48fcde26285d26e4b71193bff6f1d809031a03',
                repo: article.data.repositoryName,
                owner: article.data.gitUserName,
                admin: [article.data.gitUserName],
                number: article.data.issueId,
                id: article.data.id,      // Ensure uniqueness and length less than 50
                distractionFreeMode: true  // Facebook-like distraction free mode
            });

            gitalk.render('gitalk-container');
            hljs.initHighlighting();
            window._hmt.push(['_trackPageview', `/articles/${article.data.id}`]);
        });
    }

    render() {

        return (
            <div>
                <Spin spinning={this.state.spinning} size="large"/>
                <h1>{this.state.article.title}</h1>
                <article className="markdown-body" dangerouslySetInnerHTML={{__html: this.state.article.body || this.state.error}}>
                </article>
                <div className="suspension-panel">
                    <button title="回到顶部" className="btn to-top-btn" onClick={this.backTop}>
                        <i className="ion-android-arrow-dropup"/>
                    </button>
                </div>
                <div id="gitalk-container" className="comment-content" />
            </div>
        );
    }
}

export default Article;