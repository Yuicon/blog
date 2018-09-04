import React, {Component} from "react";

/**
 * @author Yuicon
 */

class Entry extends Component {

    handleOnClick = () => {
        this.props.history.push(`/articles/${this.props.article.id}`);
    };

    render() {

        return (
            <div className="entry">
                <div>
                    <p className="article-title" onClick={this.handleOnClick}>{this.props.article.title}</p>
                </div>
                <div className="details">
                    <span>创建于：{this.props.article.createdAt.replace(/Z/g,' ').replace(/T/g, ' ')} · </span>
                    <span>最后更新于：{this.props.article.updatedAt.replace(/Z/g,' ').replace(/T/g, ' ')} · </span>
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