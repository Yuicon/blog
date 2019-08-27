import React, {Component} from "react";

/**
 * @author Yuicon
 */

export default class V2exArticle extends Component {

    render() {

        return (
            <article dangerouslySetInnerHTML={{__html: this.props.article.body}}/>
        );
    }
}