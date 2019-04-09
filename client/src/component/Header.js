/**
 * @author Yuicon
 */
import React, {Component} from "react";
import UserInfo from "./UserInfo";

class Header extends Component {

    render() {
        return (
            <header className="App-header">
                <div>
                    <h1 className="App-title"><a href="/">Welcome to Yuicon Blog</a></h1>
                </div>
                <UserInfo/>
            </header>
        );
    }
}

export default Header;
