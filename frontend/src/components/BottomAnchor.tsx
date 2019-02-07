import * as React from "react";
import {Component} from "react";

interface BottomAnchorProps {
    shouldScrollToBottom: boolean|null,
    toggleScroll: () => void
}

export class BottomAnchor extends Component<BottomAnchorProps> {
    private node: HTMLDivElement | null = null;
    render() {
        return <div ref={node => this.node = node} />
    }

    componentDidUpdate() {
        this.scrollToBottom()
    }

    componentDidMount() {
        this.scrollToBottom();
    }

    private scrollToBottom() {
        if (this.node && this.props.shouldScrollToBottom) {
            this.node.scrollIntoView({behavior: "smooth"});
            this.props.toggleScroll();
        }
    }
}

