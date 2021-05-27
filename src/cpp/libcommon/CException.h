#ifndef _CEXCEPTION_H_
#define _CEXCEPTION_H_

#include "CObject.h"

#define COMMON_NS common

namespace  _COMMON_NS
{
    class CException : public CObject, public std::exception
    {
        private:
            std::string mMsg;

        public:
            CException(const char* msg) : std::exception()
            {
                mMsg = std::string(msg);
            }

            CException(std::string msg) : std::exception()
            {
                mMsg = msg;
            }

            _OVERRIDE std::string getClassName() const;
            _OVERRIDE void log() const;
            _OVERRIDE std::string toString() const;
            _OVERRIDE const char* what() const noexcept
            {
                return (const char*)mMsg.c_str();
            }
    };
}
#endif