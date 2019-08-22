import React from 'react'
import BraftEditor from 'braft-editor'
import 'braft-editor/dist/index.css'

export default class Editor extends React.Component {

    state = {
        editorState: null
    };

    submitContent = async () => {
        // 在编辑器获得焦点时按下ctrl+s会执行此方法
        // 编辑器内容提交到服务端之前，可直接调用editorState.toHTML()来获取HTML格式的内容
        this.props.onChange(this.state.editorState.toRAW());
    };

    handleEditorChange = (editorState) => {
        this.setState({editorState}, () => this.props.onChange(this.state.editorState.toRAW()))
    };

    render () {

        const { editorState } = this.state;

        return (
            <BraftEditor
                value={editorState}
                onChange={this.handleEditorChange}
                onSave={this.submitContent}
            />
        )

    }

}