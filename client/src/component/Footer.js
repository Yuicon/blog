/**
 * @author Yuicon
 */
import React, {Component} from "react";

class Footer extends Component {

    render() {

        return (
            <div className="footer">
                <div className="friend-link">
                    <p>友情链接：</p>
                    {/*<a target="_blank" href="http://jiangqz.xyz/">jiangqz</a>*/}
                </div>
                <div className="license">
                    ©2019 Yuicon
                    浙ICP备18037558号
                </div>
            </div>
        );
    }
}

export default Footer;
