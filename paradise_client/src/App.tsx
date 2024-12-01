import React from "react";

import AppHeader from "@/compositions/AppHeader/AppHeader";
import AppView from "@/compositions/AppView/AppView";
import AppFooter from "@/compositions/AppFooter/AppFooter";
import AppSidebar from "@/compositions/AppSidebar/AppSidebar";

import "./App.scss";
import RegisterModal from "@/components/RegisterModal/RegisterModal";
import LoginModal from "@/components/LoginModal/LoginModal";

type AppState = {
    isSidebarOpen: boolean;
    isRegisterModalOpen: boolean;
    isLoginModalOpen: boolean;
};

type AppProps = {};

class App extends React.Component<AppProps, AppState> {
    state: AppState = {
        isSidebarOpen: false,
        isRegisterModalOpen: false,
        isLoginModalOpen: false
    };

    toggleAppSidebar() {
        this.setState({
            isSidebarOpen: !this.state.isSidebarOpen
        });
    }

    toggleRegisterModal(value: boolean) {
        this.setState({
            isRegisterModalOpen: value
        });
    }

    toggleLoginModal(value: boolean) {
        this.setState({
            isLoginModalOpen: value
        });
    }

    componentDidMount() {}

    render() {
        return (
            <div className="App">
                <RegisterModal
                    isOpen={this.state.isRegisterModalOpen}
                    toggleOpen={this.toggleRegisterModal.bind(this)}
                />
                <LoginModal isOpen={this.state.isLoginModalOpen} toggleOpen={this.toggleLoginModal.bind(this)} />
                <div className={"AppSidebar_container" + (this.state.isSidebarOpen ? "" : " Sidebar_Close")}>
                    <AppSidebar
                        isOpen={this.state.isSidebarOpen}
                        toggleSidebar={this.toggleAppSidebar.bind(this)}
                        isLoginOpen={this.state.isLoginModalOpen}
                        isRegisterOpen={this.state.isRegisterModalOpen}
                        toggleLoginModal={this.toggleLoginModal.bind(this)}
                        toggleRegisterModal={this.toggleRegisterModal.bind(this)}
                    />
                </div>
                <div className="AppContent_container">
                    <AppHeader
                        isLoginOpen={this.state.isLoginModalOpen}
                        isRegisterOpen={this.state.isRegisterModalOpen}
                        toggleLoginModal={this.toggleLoginModal.bind(this)}
                        toggleRegisterModal={this.toggleRegisterModal.bind(this)}
                    />
                    <AppView />
                    <AppFooter />
                </div>
            </div>
        );
    }
}
export default App;
