import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import background from '../../../assets/img/background.png';
import mainImg from '../../../assets/img/mainImg.png';

import { Header } from '../../../styles/StyledComponents';

interface MentColorProps {
    fontcolor?: string; // Define the fontColor prop
}

const Main = () => {
    return (
        <Container>
            <Header color="#5e88b7"> 간편 예약은 "PICKER PENG" </Header>
            <RemarkButton> 파리바게트 다이아몬드광장점 </RemarkButton>
            <MentContainer>
                <FirstMent fontcolor="#C0EDFF">지금&nbsp;</FirstMent>
                <FirstMent>케이크를 예약</FirstMent>
                <FirstMent fontcolor="#C0EDFF">하면</FirstMent>
            </MentContainer>

            <MentContainer>
                <SecondMent fontcolor="#D63025">커스텀 토퍼</SecondMent>
                <SecondMent>가 무료!</SecondMent>
            </MentContainer>
            <img style={{ paddingTop: '20px' }} alt="mainImg" src={mainImg} width="360px" />
            <Link to="/menu">
                <GradientButton> 지금 예약하고 토퍼 받기 </GradientButton>
            </Link>
            <Link to="https://naver.me/5rZHj3AQ" target="_blank">
                <Text> &nbsp;매장 정보 확인하기&nbsp;</Text>
            </Link>
        </Container>
    );
};

export default Main;

const Container = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    background-image: url(${background});
    min-height: calc(var(--vh, 1vh) * 100);
    width: 100%;

    @media (min-width: 576px) {
        width: 576px;
    }
`;

const RemarkButton = styled.div`
    margin: 30px 0 30px 0;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    width: 250px;
    height: 40px;
    border-radius: 16px;
    background-color: #023586;
    color: white;
    font-family: 'BMDOHYEON';
    font-size: 16px;
    padding-top: 2px;
    letter-spacing: 0.5px;
`;

const MentContainer = styled.div`
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    margin-bottom: 3px;
`;
const FirstMent = styled.div<MentColorProps>`
    color: ${(props) => props.fontcolor || 'white'};
    font-family: 'TmonMonsori';
    font-size: 26px;
    text-shadow: -1px -1.5px 0 black, 1px -1px 0 black, -1px 1px 0 black, 1px 1px 0 black;
    letter-spacing: 1px;
`;

const SecondMent = styled.div<MentColorProps>`
    color: ${(props) => props.fontcolor || 'black'};
    font-family: 'TmonMonsori';
    font-size: 42px;
    letter-spacing: 1px;
    text-shadow: -2px -2px 0 white, 1px -2px 0 white, -2px 2px 0 white, 2px 2px 0 white, 2px 4px 5px black;
`;

const GradientButton = styled.button`
    margin: 30px 0 0 0;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 360px;
    height: 70px;
    border-radius: 16px;
    color: white;
    font-family: 'BMDOHYEON';
    font-size: 22px;
    box-shadow: 0px 2px 10px black;
    background: linear-gradient(to bottom, rgba(2, 53, 134, 0.8), rgba(79, 137, 229, 0.8), rgba(2, 53, 134, 0.8)),
        #023586;
    letter-spacing: 0.8px;
`;

const Text = styled.button`
    margin: 30px 0 50px 0;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #575756;
    font-family: 'BMDOHYEON';
    font-size: 20px;
    letter-spacing: 0.5px;
    text-decoration: underline;
    text-underline-offset: 7px;
`;
