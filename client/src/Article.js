import React, {Component} from "react";
import {api} from "./api";
import 'highlight.js/styles/github-gist.css';
import hljs from 'highlight.js';
import 'gitalk/dist/gitalk.css'
import Gitalk from 'gitalk'


/**
 * @author Yuicon
 */

class Article extends Component {

    constructor() {
        super();
        this.state = {
            article: {},
            error: ""
        };
    }

    backTop = () => {
        window.scrollTo(0,0);
    };

    async componentDidMount() {
        const article = await api.getArticleById(this.props.match.params.id);
        this.setState({article, error: "<h1>没有文章哦！</h1>"}, () => {
            const gitalk = new Gitalk({
                clientID: '1f4bdb550130e267946f',
                clientSecret: '1b48fcde26285d26e4b71193bff6f1d809031a03',
                repo: article.repositoryName,
                owner: article.gitUserName,
                admin: [article.gitUserName],
                number: article.issueId,
                id: article.id,      // Ensure uniqueness and length less than 50
                distractionFreeMode: true  // Facebook-like distraction free mode
            });

            gitalk.render('gitalk-container');
            hljs.initHighlighting();
        });
        console.log(article);
    }

    render() {

        return (
            <div>
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