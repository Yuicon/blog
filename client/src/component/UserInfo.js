/**
 * @author Yuicon
 */
import React, {Component} from "react";

class UserInfo extends Component {

    constructor() {
        super();
        this.state = {
            user: null,
        };
    }

    render() {

        return (
            <div className="username">
                <h3>{this.state.user ? this.state.user.username : "未登录"}</h3>
            </div>
        );
    }
}

export default UserInfo;
