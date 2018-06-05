import * as React from "react";
import {Component} from "react";

interface BottomAnchorProps {
    shouldScrollToBottom: boolean|null,
    toggleScroll: () => void
}

export class BottomAnchor extends Component<BottomAnchorProps> {
    private node: HTMLDivElement | null;
    render() {
        console.log("rendering!")
        return <div ref={node => this.node = node} />
    }

    componentDidUpdate() {
        this.scrollToBottom()
    }

    componentDidMount() {
        this.scrollToBottom();
    }

    private scrollToBottom() {
        console.log("didmount", this.node, this.props.shouldScrollToBottom);
        if (this.node && this.props.shouldScrollToBottom) {
            this.node.scrollIntoView({behavior: "smooth"});
            this.props.toggleScroll();
        }
    }
}

