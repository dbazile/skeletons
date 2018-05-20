import * as React from 'react'
import { shallow } from 'enzyme'

import { DiscAnimation, MercuryAnimation } from '../../../src/components/ui/ActivityIndicator'


describe('<DiscAnimation/>', () => {
    it('can render', () => {
        const component = shallow(
            <DiscAnimation/>,
        )

        expect(component.hasClass('disc')).toBeTruthy()
    })

    it('honors classname from props', () => {
        const component = shallow(
            <DiscAnimation
                className="test-classname"
            />,
        )

        expect(component.hasClass('test-classname')).toBeTruthy()
    })
})


describe('<MercuryAnimation/>', () => {
    it('can render', () => {
        const component = shallow(
            <MercuryAnimation/>,
        )

        expect(component.hasClass('mercury')).toBeTruthy()
    })

    it('honors classname from props', () => {
        const component = shallow(
            <MercuryAnimation
                className="test-classname"
            />,
        )

        expect(component.hasClass('test-classname')).toBeTruthy()
    })
})
