import React, {Component} from 'react';
import './App.css';
import {BrowserRouter as Router,Route} from 'react-router-dom'
import New from "./New";
import Home from "./Home";

class App extends Component {



    render() {



        return (
            <Router>
                <div className="App">
                    <header className="App-header">
                        <h1 className="App-title">Welcome to Yuicon Blog</h1>
                    </header>
                    <Route exact path="/article/new" component={New}/>
                    <Route exact path="/" component={Home}/>
                </div>
            </Router>
        );
    }
}

export default App;
