import React, {Component} from "react";
import BraftEditor from 'braft-editor'

/**
 * @author Yuicon
 */

export default class BraftArticle extends Component {

    renderContent = () => {
        const editorState = BraftEditor.createEditorState(this.props.article.body);
        const content = editorState.toHTML();
        return {__html: content};
    };

    render() {
        return (
            <article dangerouslySetInnerHTML={this.renderContent()}/>
        );
    }
}