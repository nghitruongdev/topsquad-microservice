import React, { useState, createContext, ReactNode } from 'react';

interface ContextProps {
  isOpen: boolean;
  setIsOpen: (isOpen: boolean) => void;
  handleClose: () => void;
}

interface SidebarProviderProps {
  children: ReactNode;
}

const Context = createContext<ContextProps | null>(null)

const SidebarProvider: React.FC<SidebarProviderProps> = ({ children }) => {
  const [isOpen, setIsOpen] = useState(false);

  const handleClose = () => {
    setIsOpen(false);
  };

  return <Context.Provider value={{ isOpen, setIsOpen, handleClose }}>{children}</Context.Provider>;
};

const SidebarContext = {
  Provider: SidebarProvider,
  useContext: () => {
    const ctx = React.useContext(Context)
    if (!ctx) throw new Error("useSidebarContext must be used within a SidebarContextProvider")
    return ctx
  }
}

export default SidebarContext