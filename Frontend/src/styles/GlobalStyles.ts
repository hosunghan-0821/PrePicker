import { createGlobalStyle } from 'styled-components';

const GlobalStyles = createGlobalStyle`

  html {
    /* background-color: #C7D3E6; */
    font-size: 62.5%; //1rem을 10px로 변환
    color: black;
  }
  
  * {
    box-sizing: border-box;

    font-size: 1.6rem;
    font-family: 'Noto Sans KR', sans-serif;
    font-weight: 400;
    letter-spacing: -0.5px;
  }
  
  body {
    box-sizing: border-box;
    font-size: 1.6rem;
    font-family: 'Noto Sans KR', sans-serif;
    font-weight: 400;
    letter-spacing: -0.5px;
    overflow: overlay;
    cursor: default;
  }


  button {
    border: 0;
    background-color: transparent;
    color: #023586;
    cursor: pointer;
  }

  textarea,
  input {
    border: none;
    background-color: transparent;
  }

  ::placeholder {
    color: white;
  }

  a {
    text-decoration: none;
    color: gray;
  }

  @font-face {
    font-family: 'TmonMonsori';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/TmonMonsori.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
  @font-face {
	font-family: "Happiness-Sans-Title";
	src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2205@1.0/Happiness-Sans-Title.woff2")
		format("woff2");
	font-weight: normal;
	font-style: normal;
}

@font-face {
    font-family: 'BMDOHYEON';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

`;

export default GlobalStyles;
