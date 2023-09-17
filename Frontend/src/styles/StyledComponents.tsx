import { styled } from 'styled-components';

interface HeaderProps {
    color?: string; // Define the fontColor prop
}

export const Header = styled.div<HeaderProps>`
    height: 60px;
    background-color: ${(props) => props.color || '#023586'};
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    color: white;
    font-size: 22px;
    font-family: 'BMDOHYEON';
`;
