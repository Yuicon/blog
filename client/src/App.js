import React, {Component} from 'react';
import './App.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import New from "./component/New";
import Home from "./component/Home";
import IssueArticle from "./component/article/IssueArticle";
import Footer from "./component/Footer";
import Header from "./component/Header";
import RecordList from "./component/record/RecordList";
import Contribute from "./component/article/Contribute";
import Article from "./component/article/Article";

class App extends Component {

    render() {

        return (
            <Router>
                <div className="App">
                    <Header />
                    <div className="content">
                        <Route exact path="/" component={Home}/>
                        <Route exact path="/article/new" component={New}/>
                        <Route exact path="/articles/:id" component={IssueArticle}/>
                        <Route exact path="/v2/articles/:id" component={Article}/>
                        <Route exact path="/record" component={RecordList}/>
                        <Route exact path="/write" component={Contribute}/>
                    </div>
                    <Footer/>
                </div>
            </Router>
        );
    }
}

export default App;
