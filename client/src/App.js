import React, {Component} from 'react';
import './App.css';
import {BrowserRouter as Router,Route} from 'react-router-dom'
import New from "./New";
import Home from "./Home";
import Article from "./Article";

class App extends Component {

    render() {

        return (
            <Router>
                <div className="App">
                    <header className="App-header">
                        <h1 className="App-title"><a href="/">Welcome to Yuicon Blog</a></h1>
                    </header>
                    <Route exact path="/" component={Home}/>
                    <Route exact path="/article/new" component={New}/>
                    <Route exact path="/articles/:id" component={Article}/>
                </div>
            </Router>
        );
    }
}

export default App;
