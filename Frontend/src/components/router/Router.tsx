import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Main from '../pages/main/Main';
import MenuList from '../pages/menu/MenuList';

const Router = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Main />} />
                <Route path="/menu" element={<MenuList />} />
            </Routes>
        </BrowserRouter>
    );
};

export default Router;
