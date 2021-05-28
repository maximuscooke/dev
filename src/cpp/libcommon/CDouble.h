#ifndef _CDOUBLE_H_
#define _CDOUBLE_H_

#include "CObject.h"

namespace  _COMMON_NS
{
    class CDouble : public CObject
    {
        private:
            Double mValue;

        public:
            CDouble()
            {
                this->mValue = 0.0;
            }
            
            CDouble(Double value)
            {
                this->mValue = value;
            }

            _STATIC Double getMax();
            _STATIC Double getMin();

            std::string getClassName() const _OVERRIDE;
            void log() const _OVERRIDE;
            std::string toString() const _OVERRIDE;
            Integer compare(CObject* pObj) _OVERRIDE;

            _OP_NUMERIC(CDouble, Double, mValue)

            _PROPERTY(Double, Value, mValue)
     };
}
#endif