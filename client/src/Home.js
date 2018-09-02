import React, {Component} from "react";
import {api} from "./api";
import Entry from "./Entry";

/**
 * @author Yuicon
 */

class Home extends Component {

    constructor() {
        super();
        this.state = {
            articles: []
        }
    }

    async componentDidMount() {
        const body = await api.getArticles();
        console.log(body);
        this.setState({
            articles: body.content
        });
    }

    render() {

        const entryList = this.state.articles.map(article => <Entry history={this.props.history} article={article} key={article.id}/>);

        return (
            <div className="entry-list">
                {entryList}
            </div>
        );
    }
}

export default Home;