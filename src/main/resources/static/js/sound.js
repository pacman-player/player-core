
const log = console.log;
const app = new PIXI.Application();
document.body.appendChild(app.view);

const buttonsHolder = new PIXI.Container();
buttonsHolder.scale.set(0.5);
app.stage.addChild(buttonsHolder);

// buttonsHolder
const makeImageButton = (audioMP3) =>
{
    const sound = new Howl({
        src: [audioMP3]
    });
    sound.play()
};

makeImageButton(
    'media/GorProject_LA.mp3',
);
