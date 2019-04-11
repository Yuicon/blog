import React, {Component} from 'react';
import './App.css';
import {BrowserRouter as Router,Route} from 'react-router-dom'
import New from "./component/New";
import Home from "./component/Home";
import Article from "./component/Article";
import Footer from "./component/Footer";
import Header from "./component/Header";
import Record from "./component/record/Record";

class App extends Component {

    render() {

        return (
            <Router>
                <div className="App">
                    <Header />
                    <Route exact path="/" component={Home}/>
                    <Route exact path="/article/new" component={New}/>
                    <Route exact path="/articles/:id" component={Article}/>
                    <Route exact path="/record" component={Record}/>
                    <Footer/>
                </div>
            </Router>
        );
    }
}

export default App;
