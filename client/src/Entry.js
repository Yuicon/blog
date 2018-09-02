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
                <p onClick={this.handleOnClick}>{this.props.article.title}</p>
            </div>
        );
    }
}

export default Entry;