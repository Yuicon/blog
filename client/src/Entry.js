import React, {Component} from "react";

/**
 * @author Yuicon
 */

class Entry extends Component {

    render() {

        return (
            <div className="entry">
                <p>{this.props.article.title}</p>
            </div>
        );
    }
}

export default Entry;