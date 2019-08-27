import React, {Component} from "react";
import 'highlight.js/styles/github-gist.css';
import hljs from 'highlight.js';
import 'gitalk/dist/gitalk.css'

/**
 * @author Yuicon
 */

export default class MarkDownArticle extends Component {

    async componentDidMount() {
        hljs.initHighlighting();
    }

    render() {

        return (
            <article className="markdown-body" dangerouslySetInnerHTML={{__html: this.props.article.body}}/>
        );
    }
}