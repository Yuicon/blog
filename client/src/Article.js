import React, {Component} from "react";
import {api} from "./api";

/**
 * @author Yuicon
 */

class Article extends Component {

    constructor() {
        super();
        this.state = {
            article: {}
        }
    }

    async componentDidMount() {
        const article = await api.getArticleById(this.props.match.params.id);
        this.setState({article});
        console.log(article);
    }

    render() {

        return (
            <div>
                <h1>{this.state.article.title}</h1>
                <article className="markdown-body" dangerouslySetInnerHTML={{__html: this.state.article.body || "<h1>没有文章哦！</h1>"}}>
                </article>
            </div>
        );
    }
}

export default Article;