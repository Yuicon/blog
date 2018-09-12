import React, {Component} from "react";
import {api} from "./api";
import 'gitment/style/default.css'
import Gitment from 'gitment'

/**
 * @author Yuicon
 */

class Article extends Component {

    constructor() {
        super();
        this.state = {
            article: {},
            error: ""
        }
    }

    backTop = () => {
        window.scrollTo(0,0);
    };

    async componentDidMount() {
        const article = await api.getArticleById(this.props.match.params.id);
        this.setState({article, error: "<h1>没有文章哦！</h1>"}, () => {
            const gitment = new Gitment({
                id: `${article.id}`,
                owner: "Yuicon",
                repo: "comment",
                oauth: {
                    client_id: '1f4bdb550130e267946f',
                    client_secret: '1b48fcde26285d26e4b71193bff6f1d809031a03',
                },
            });
            gitment.render('container');
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
                <div className="comment-full">
                    <div id="container" className="comment-content"/>
                </div>
            </div>
        );
    }
}

export default Article;