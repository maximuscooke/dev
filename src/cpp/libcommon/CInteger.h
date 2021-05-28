#ifndef _CINTEGER_H_
#define _CINTEGER_H_

#include "CObject.h"

namespace  _COMMON_NS
{
    class CInteger : public CObject
    {
        private:
            Integer mValue;

        public:
            CInteger()
            {
                this->mValue = 0;
            }

            CInteger(Integer value)
            {
                this->mValue = value;
            }

            _STATIC Integer getMax(); 
            _STATIC Integer getMin();
  
            std::string getClassName() const _OVERRIDE;
            void log() const _OVERRIDE;
            std::string toString() const _OVERRIDE;
            Integer compare(CObject* pObj) _OVERRIDE;

            _OP_NUMERIC(CInteger, Integer, mValue)
            _OP_NUMERIC_MOD(CInteger, Integer, mValue)
            
            _PROPERTY(Integer, Value, mValue)
     };
}
#endif