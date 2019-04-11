/**
 * @author Yuicon
 */
import React, {Component} from "react";
import {message} from "antd";
import {TOKEN_KEY} from "../../constant";
import {recordApi} from "../../api/recordApi";

class Record extends Component {

    constructor(props) {
        super(props);
        this.state = {
            records: [],
        };
    }

    async componentDidMount() {
        if (!localStorage.getItem(TOKEN_KEY)) {
            this.props.history.push("/");
        }
        this.setState({spinning: true});
        const body = await recordApi.list();
        if (body.success) {
            this.setState({records: body.data});
        } else {
            message.error(body.message);
        }
        this.setState({
            spinning: false
        });
    }

    render() {

        return (
            <div>
                {this.state.records.forEach(record => <div><h3>{record.source}</h3></div>)}
            </div>
        );
    }
}

export default Record;