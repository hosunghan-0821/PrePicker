import { useCallback, useEffect, useState } from 'react';
import { styled } from 'styled-components';
import './App.css';
import Router from './components/router/Router';
import GlobalStyles from './styles/GlobalStyles';

function App() {
    const [vh, setVh] = useState(window.innerHeight * 0.01);

    const screenSize = useCallback(() => {
        setVh(window.innerHeight * 0.01);
        document.documentElement.style.setProperty('--vh', `${vh}px`);
    }, [vh]);

    useEffect(() => {
        screenSize();
        window.addEventListener('resize', screenSize);
        return () => window.removeEventListener('resize', screenSize);
    }, [screenSize]);

    return (
        <>
            <GlobalStyles />
            <Container>
                <Router />
            </Container>
        </>
    );
}

export default App;

const Container = styled.div`
    background-color: transparent;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border: black;
    font-size: 1.5em;
    min-height: calc(var(--vh, 1vh) * 100);
    width: 100%;

    @media (min-width: 576px) {
        display: flex;
        flex-direction: column;
        justify-content: center;
        background-color: #c7d3e6;
        font-size: 2em;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
`;
