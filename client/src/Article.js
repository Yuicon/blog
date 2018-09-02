import React, {Component} from "react";
import {api} from "./api";

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

    async componentDidMount() {
        const article = await api.getArticleById(this.props.match.params.id);
        this.setState({article, error: "<h1>没有文章哦！</h1>"});
        console.log(article);
    }

    render() {

        return (
            <div>
                <h1>{this.state.article.title}</h1>
                <article className="markdown-body" dangerouslySetInnerHTML={{__html: this.state.article.body || this.state.error}}>
                </article>
            </div>
        );
    }
}

export default Article;