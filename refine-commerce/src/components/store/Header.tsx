import React, { useEffect, useState } from "react";
import { Link, NavLink } from "react-router-dom";
import { Bs0Square, Bs1Circle, Bs6Square, BsBack, BsBadge3D, BsBag, BsBagCheck, BsSafe2, BsShieldLock, BsStackOverflow } from "react-icons/bs";
import Logo from "../../img/logo.svg";
import SidebarContext from "../../contexts/sidebar-context";
import CartContext from "../../contexts/cart-context";
import { LoginOutlined, LogoutOutlined, ProjectOutlined, SecurityScanOutlined, SecurityScanTwoTone, UserDeleteOutlined, UserOutlined, UserSwitchOutlined } from "@ant-design/icons";
import { Popover } from "antd";
import { useGetIdentity } from "@refinedev/core";
import { FiSettings } from "react-icons/fi";

export const Header: React.FC = () => {
  const [isActive, setIsActive] = useState(false);
  const { isOpen, setIsOpen } = SidebarContext.useContext()
  const { itemAmount } = CartContext.useContext()
  const { data: user } = useGetIdentity()
  useEffect(() => {
    const handleScroll = () => {
      window.scrollY > 60 ? setIsActive(true) : setIsActive(false);
    };
    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <header
      className={`${isActive ? "bg-white py-4 shadow-md" : "bg-none py-6"} fixed w-full z-10 lg:px-8 transition-all`}
    >
      <div className="container mx-auto flex items-center justify-between h-full">
        <Link to={"/"}>
          <div className="w-[40px]">
            <img src={Logo} alt="" className="w-full" />
          </div>
        </Link>

        {/* cart */}
        <div className="flex ">
          <div onClick={() => setIsOpen(!isOpen)} className="cursor-pointer flex relative">
            <BsBag className="text-2xl" />
            <div className="bg-red-500 absolute -right-2 -bottom-2 text-[12px] w-[18px] h-[18px] text-white rounded-full flex justify-center items-center select-none">
              {itemAmount}
            </div>
          </div>
          <>
            {!user && <Link className="cursor-pointer flex relative mx-4" to="/login">
              <UserOutlined className="text-2xl" />
            </Link>}
            {!!user && <Popover trigger={'hover'}
              content={<p className="min-w-[100px] flex flex-col justify-center items-center">
                <span className="text-xs">Welcome back,</span>
                <span className="text-md text-zinc-700"> {(user as any).name}</span>
                <hr className="text-sm w-full mt-2" />
                <Link className="cursor-pointer flex relative gap-2 mt-2 justify-center" to="/logout">
                  Logout
                  <LogoutOutlined />
                </Link>
              </p>}>
              <UserOutlined className="text-2xl mx-4 cursor-pointer text-zinc-700" />
            </Popover>}
          </>
          {!!user && <NavLink to={"/admin"} className='text-base flex text-zinc-700 hover:text-zinc-800'><BsStackOverflow className="text-2xl" /><span className="text-base">Admin</span></NavLink>}
        </div>

      </div>
    </header>
  );
};