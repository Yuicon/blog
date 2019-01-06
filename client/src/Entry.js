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
                       href={`/articles/${this.props.article.id}`}>
                        <p>{this.props.article.title}</p>
                    </a>
                </div>
                <div className="details">
                    <span>创建于：{this.props.article.createdAt.replace(/Z/g, ' ').replace(/T/g, ' ')} · </span>
                    <span>最后更新于：{this.props.article.updatedAt.replace(/Z/g, ' ').replace(/T/g, ' ')} · </span>
                    <a href={`https://github.com/${this.props.article.gitUserName}/${this.props.article.repositoryName}`}
                       target="_blank"
                    >
                        仓库地址
                    </a>
                </div>
            </div>
        );
    }
}

export default Entry;