import React, {Component} from "react";

/**
 * @author Yuicon
 */

class Entry extends Component {

    render() {

        return (
            <div className="entry">
                <div>
                    <a className="article-title"
                       target="_blank"
                       href={`/v2/articles/${this.props.article.id}`}>
                        <p>{this.props.article.title}</p>
                    </a>
                </div>
                <div className="details">
                    <span>创建于：{this.props.article.createTime}</span>
                </div>
            </div>
        );
    }
}

export default Entry;